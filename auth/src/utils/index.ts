import {Request, Response, NextFunction, RequestHandler} from "express";
import path from "path";

import { create } from "../db";

const appendRoot = (req: Request, res: Response, next: NextFunction) => {
    req.root = path.dirname(path.dirname(__dirname));
    next();
};

const injectDatabase = (req: Request, res: Response, next: NextFunction) => {
    create(req.root).then(db => {
        req.db = db;
        next();
    });
};

const fromAsync = (fn: RequestHandler) => (req: Request, res: Response, next: NextFunction) => {
    Promise.resolve(fn(req, res, next)).catch(next);
};

const redirect = (newPath: string) => (req: Request, res: Response, next: NextFunction) => {
    res.redirect(newPath);
    next();
};

export default { appendRoot, injectDatabase, fromAsync, redirect };
