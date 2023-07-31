package model

import (
	"time"
)

type Integration struct {
	ID string `json:"id"`
	Url string `json:"url,omitempty"`
	Port string `json:"port,omitempty"`
	Interval int `json:"interval,omitempty"`
	MonitoringAt time.Time `json:"monitoring_at,omitempty"`
	NextMonitoring time.Time `json:"next_monitoring,omitempty"`
}