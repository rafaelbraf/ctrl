package model

import (
	"go.mongodb.org/mongo-driver/bson/primitive"
)

type Monitoring struct {
	ObjectID primitive.ObjectID `bson:"_id,omitempty"`
	IntegrationID string `bson:"integrationId"`
	Result string `bson:"result"`
	Status string `bson:"status"`
}