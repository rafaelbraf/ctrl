import jwt, { SignOptions } from "jsonwebtoken";
import config from "config";

export const signJwt = (payload: Object, options: SignOptions = {}) => {
    const privateKey = config.get<string>('jwtKey');

    return jwt.sign(payload, privateKey);
};

export const verifyJwt = <T>(token: string): T | null => {
    try {
        const publicKey = config.get<string>('jwtKey');

        return jwt.verify(token, publicKey) as T;
    } catch (error) {
        return null;
    }
};