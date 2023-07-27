import { createClient } from "redis";
import config from "config";

const redisHost = config.get<string>('redisHost');
const redisPort = config.get<string>('redisPort');

const redisUrl = `redis://${redisHost}:${redisPort}`;
const redisClient = createClient({
    url: redisUrl
});

const connectRedis = async () => {
    try {
        await redisClient.connect();
        console.log("Conectado ao Redis Client...");
    } catch (err: any) {
        console.log("Erro ao se conectar ao Redis Client: " + err.message);
        setTimeout(connectRedis, 5000);
    }
}

connectRedis();

redisClient.on('error', (err) => console.log(err));

export default redisClient;