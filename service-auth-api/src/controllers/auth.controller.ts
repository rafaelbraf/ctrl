import config from "config";
import { CookieOptions, NextFunction, Request, Response } from "express";
import { CreateUserInput, LoginUserInput } from "../schemas/user.schema";
import { createUser, findUser, signToken } from "../services/user.service";
import AppError from "../utils/appError";

export const excludedFields = ['password'];

const accessTokenExpiresIn: number = config.get<number>('accessTokenExpiresIn') * 60 * 1000;

const accessTokenCookieOptions: CookieOptions = {
    expires: new Date(Date.now() + accessTokenExpiresIn),
    maxAge: accessTokenExpiresIn,
    httpOnly: true,
    sameSite: 'lax'
};

if (process.env.NODE_ENV === 'production') {
    accessTokenCookieOptions.secure = true;
}

export const registerHandler = async (
    req: Request<{}, {}, CreateUserInput>,
    res: Response,
    next: NextFunction
) => {
    try {
        const user = await createUser({
            email: req.body.email,
            name: req.body.name,
            password: req.body.password
        });

        res.status(201).json({
            status: 'success',
            data: {
                user
            }
        });
    } catch (err: any) {
        if (err.code === 11000) {
            return res.status(409).json({
                status: 'fail',
                message: 'Email already exist'
            });
        }

        next(err);
    }
};

export const loginHandler = async (
    req: Request<{}, {}, LoginUserInput>,
    res: Response,
    next: NextFunction
) => {
    try {
        const user = await findUser({ email: req.body.email });
        if (!user || !(await user.comparePassword(req.body.password, user.password))) {
            return next(new AppError('Invalid email or password', 401));
        }

        const accessToken = await signToken(user);
        
        res.cookie('accessToken', accessToken, accessTokenCookieOptions);
        res.cookie('logged_in', true, {
            ...accessTokenCookieOptions,
            httpOnly: false
        });

        res.status(200).json({
            status: 'success',
            accessToken
        });
    } catch (err: any) {
        next(err);
    }
};