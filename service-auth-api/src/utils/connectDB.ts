import mongoose from "mongoose";
import config from "config";

const dbDriver = "mongodb";
const dbName = config.get<string>('dbName');
const dbUser = config.get<string>('dbUser');
const dbPass = config.get<string>('dbPass');
const dbPort = config.get<string>('dbPort');
const dbHost = config.get<string>('dbHost');

const dbUrl = `${dbDriver}://${dbUser}:${dbPass}@${dbHost}:${dbPort}/${dbName}?authSource=admin`;

const connectDB = async () => {
    try {
        await mongoose.connect(dbUrl);
        console.log("Conectado ao Banco de Dados com sucesso...");
    } catch (error: any) {
        console.log("Erro ao tentar se conectar ao Banco de Dados.: " + error.message);
        setTimeout(connectDB, 5000);
    }
};

export default connectDB;