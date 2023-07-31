package db

import (
	"fmt"
	"context"
	"log"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

var Coll *mongo.Collection
var CtxDB = context.TODO()

func ConnectDb() {
	clientOptions := options.Client().ApplyURI(URL_INTEGRATIONS)

	client, err := mongo.Connect(CtxDB, clientOptions)
	if err != nil {
		log.Fatal(err)
	}

	err = client.Ping(CtxDB, nil)
	if err != nil {
		log.Fatal(err)
	}

	fmt.Println("Banco de Dados conectado com sucesso!")
	Coll = client.Database(DB_NAME).Collection("monitorings")
}