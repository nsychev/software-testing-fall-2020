import "jest";
// @ts-ignore
import request from "supertest";

import * as fs from "fs";
import * as path from "path";

import app from "../src/app";


it("should return public key", async (done) => {
    const expectedKey = fs.readFileSync(
        path.resolve(__dirname, "..", "keys", "public.pem")
    ).toString();

    request(app)
        .get('/api/auth/v1/public-key')
        .expect('Content-Type', /(x-x509-ca-cert)|(x-pem-file)/)
        .expect('Content-Length', expectedKey.length.toString())
        .expect(200, expectedKey, done);
});
