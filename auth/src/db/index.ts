import low, { LowdbAsync } from "lowdb";
import FileAsync from "lowdb/adapters/FileAsync";
import path from "path";

import { User } from "../models";

export class Database {
    constructor(
        public users: User[]
    ) {}
}

export async function create(root: string): Promise<LowdbAsync<Database>> {
    const dbFilename = process.env.NODE_ENV === "test" ? "storage.test" : "storage";
    const dbPath = path.resolve(root, dbFilename);
    const adapter = new FileAsync(dbPath);
    const db = await low(adapter);

    await db.defaults(new Database([])).write();

    return db;
}
