import { LowdbAsync } from "lowdb";
import { Database } from "../../db";

declare global {
    namespace Express {
        interface Request {
            root: string;
            db: LowdbAsync<Database>;
        }
    }
}

export {};
