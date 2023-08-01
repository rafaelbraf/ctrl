package main

import (
	"net/http"
	"strconv"
	"ctrl/service-monitor-integrations/model"
	"ctrl/service-monitor-integrations/db"
	"ctrl/service-monitor-integrations/service"
	"ctrl/service-monitor-integrations/internal"
)

func init() {
	db.ConnectDb()
	internal.ConnectToRabbitMQ()
}

func main() {
	var integrations = service.GetAllIntegrations()

	for _, integration := range integrations {
		var monitoring model.Monitoring

		resp, err := http.Get(integration.Url)
		if err != nil {
			monitoring.IntegrationID = integration.ID
			monitoring.Result = "Erro"
			monitoring.Status = "Erro"

			service.CreateMonitoring(monitoring)
		}

		if resp != nil {
			if resp.StatusCode != 200 {
				monitoring.IntegrationID = integration.ID
				monitoring.Result = "Status diferente de 200. Status: " + strconv.Itoa(resp.StatusCode)
				monitoring.Status = resp.Status

				service.CreateMonitoring(monitoring)
			}

			if resp.StatusCode == 200 {
				monitoring.IntegrationID = integration.ID
				monitoring.Result = "OK"
				monitoring.Status = "Online"

				service.CreateMonitoring(monitoring)
			}
		}
	}
}