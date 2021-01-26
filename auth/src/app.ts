import bodyParser from "body-parser";
import express, { Router } from "express";
import swagger from "swagger-ui-express";
import swaggerJSDoc from "swagger-jsdoc";

import routes from "./routes";
import utils from "./utils";

const basePath = "/api/auth/v1";

const app = express();
app.use(bodyParser.json());


const router = Router();
const openapi = swaggerJSDoc({
    swaggerDefinition: {
        openapi: "3.0.0",
        info: {
            title: "hashback-auth",
            description: "Hashback Authorization Service",
            version: "1.0.0"
        },
        servers: [
            {url: basePath}
        ]
    },
    apis: ["./src/routes/*.ts"]
});

router.use(utils.appendRoot);
router.use(utils.injectDatabase);
router.get("/", utils.redirect(`${basePath}/docs`));
router.post("/users", utils.fromAsync(routes.createUser));
router.post("/token", utils.fromAsync(routes.issueToken));
router.get("/public-key", utils.fromAsync(routes.getPublicKey));
router.use("/docs", swagger.serve, swagger.setup(openapi));

app.use(basePath, router);
app.get("/", utils.redirect(basePath));

export default app;
