require('dotenv').config();

import express, { Request, Response, NextFunction } from 'express';
import config from 'config';
import connectDB from './utils/connectDB';
import cookieParser from 'cookie-parser';
import morgan from 'morgan';
import cors from "cors";
import userRouter from "./routes/user.route";
import authRouter from "./routes/auth.route";

const app = express();
app.use(express.json({ limit: '10kb' }));
app.use(cookieParser());

if (process.env.NODE_ENV === 'development') {
    app.use(morgan('dev'));
}

app.use((req: Request, res: Response, next: NextFunction) => {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Credentials", "true");
    res.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
    res.header("Access-Control-Allow-Headers", "Content-Type, Authorization");

    if (req.method === "OPTIONS") {
        return res.status(200).end();
    }
    
    app.use(cors({
        origin: config.get<string>('origin'),
        credentials: true
    }));

    next();
});

app.use('/api/users', userRouter);
app.use('/api/auth', authRouter);

app.get('/healthChecker', (req: Request, res: Response, next: NextFunction) => {
    res.status(200).json({
        status: 'success',
        message: 'Bem vindo a Api de Autenticação do Ctrl!'
    });
});

app.all('*', (req: Request, res: Response, next: NextFunction) => {
    const err = new Error(`Route ${req.originalUrl} not found`) as any;
    err.statusCode = 404;

    next(err);
});

app.use((err: any, req: Request, res: Response, next: NextFunction) => {
    err.status = err.status || 'error';
    err.statusCode = err.statusCode || 500;

    res.status(err.statusCode).json({
        status: err.status,
        message: err.message
    });
});

const port = config.get<string>('port');
app.listen(port, () => {
    console.log(`Servidor iniciado na porta: ${port}`);
    connectDB();
});