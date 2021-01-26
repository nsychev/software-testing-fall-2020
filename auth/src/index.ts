import app from "./app";

const host = process.argv[2] || "127.0.0.1";
const port = parseInt(process.argv[3], 10) || 7777;

app.listen(port, host, () => {
    // tslint:disable-next-line:no-console
    console.log(`Listening on ${host}:${port}`);
})
