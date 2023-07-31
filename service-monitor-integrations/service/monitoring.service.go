package service

import (
	"ctrl/service-monitor-integrations/model"
	"ctrl/service-monitor-integrations/db"
)

func CreateMonitoring(monitoring model.Monitoring) error {
	_, err := db.Coll.InsertOne(db.CtxDB, monitoring)

	return err
}