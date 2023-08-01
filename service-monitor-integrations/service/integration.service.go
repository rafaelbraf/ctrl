package service

import (
	"fmt"
	"net/http"
	"io/ioutil"
	"encoding/json"
	"ctrl/service-monitor-integrations/model"
)

func GetAllIntegrations() []model.Integration {
	var integrations []model.Integration

	resp, err := http.Get("http://localhost:8000/api/integrations/")
	if err != nil {
		fmt.Println("Erro ao buscar integrações")
	}

	if resp != nil {
		respBody, err := ioutil.ReadAll(resp.Body)
		if err != nil {
			fmt.Println("Erro ao ler corpo da resposta.")
		}

		resp.Body.Close()

		var respBodyString = []byte(string(respBody))

		if err := json.Unmarshal(respBodyString, &integrations); err != nil {
			fmt.Println("Erro ao transformar corpo da resposta em Integrações")
		}
	}

	return integrations
}