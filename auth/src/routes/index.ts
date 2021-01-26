import { Request, Response, NextFunction } from "express";
import fs from "fs";
import jwt from "node-webtokens";
import path from "path";

import { User } from "../models";

/**
 * @openapi
 * /users:
 *   post:
 *     summary: Creates a new user
 *     requestBody:
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             required:
 *               - username
 *               - password
 *             properties:
 *               username:
 *                 type: string
 *                 example: ivan
 *               password:
 *                 type: string
 *                 example: 123456
 *     responses:
 *       201:
 *         description: User was created.
 *       400:
 *         description: Request is invalid.
 *       409:
 *         description: Username is taken.
 *     tags:
 *       - Authorization
 */
const createUser = async (req: Request, res: Response, _: NextFunction) => {
    if (typeof req.body.username !== 'string' || !req.body.username ||
        typeof req.body.password !== 'string' || !req.body.password) {
        res.status(400);
        res.send({ error: 'invalid-request' });
        return;
    }

    const user = req.db.get('users').find({ username: req.body.username }).value();

    if (user) {
        res.status(409);
        res.send({ error: 'username-taken' })
        return;
    }

    req.db.get('users').push(
        new User(
            req.body.username,
            req.body.password
        )
    ).write();

    res.send({ ok: true });
};

/**
 * @swagger
 * /token:
 *   post:
 *     summary: Issues a token for user
 *     requestBody:
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             required:
 *               - username
 *               - password
 *             properties:
 *               username:
 *                 type: string
 *                 example: ivan
 *               password:
 *                 type: string
 *                 example: 123456
 *     responses:
 *       200:
 *         description: New token was issued.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 token:
 *                   type: string
 *                   example: AAA
 *       400:
 *         description: Request is invalid.
 *       403:
 *         description: Username or password is invalid.
 *     tags:
 *       - Authorization
 */
const issueToken = async (req: Request, res: Response, _: NextFunction) => {
    if (typeof req.body.username !== 'string' || !req.body.username ||
        typeof req.body.password !== 'string' || !req.body.password) {
        res.status(400);
        res.send({ error: 'invalid-request' });
        return;
    }

    const user = req.db.get('users').find({
        username: req.body.username,
        password: req.body.password
    }).value();

    if (!user) {
        res.status(403);
        res.send({ error: 'invalid-credentials' });
        return;
    }

    const timestamp = Math.round(+new Date() / 1000);

    const token = jwt.generate(
        'ES512',
        {
            username: user.username,
            nbf: timestamp,
            exp: timestamp + 3600,
            kid: 'auth'
        },
        fs.readFileSync(path.resolve(req.root, "keys", "private.pem"))
    );

    res.send({ token });
};

/**
 * @swagger
 * /public-key:
 *   get:
 *     summary: Returns public key for verifying tokens
 *     responses:
 *       200:
 *         description: Public key.
 *         content:
 *            application/x-pem-file:
 *             schema:
 *               type: string
 *               format: binary
 *     tags:
 *       - Authorization
 */
const getPublicKey = (req: Request, res: Response) => {
    res.sendFile(path.join("keys", "public.pem"), {
        root: req.root
    });
};

export default { createUser, issueToken, getPublicKey };
