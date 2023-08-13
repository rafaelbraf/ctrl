package internal

import (
	"github.com/streadway/amqp"
)

func ConnectToRabbitMQ() (*amqp.Connection, error) {
	conn, err := amqp.Dial("amqp://guest:guest@localhost:5672/")
	if err != nil {
		return nil, err
	}

	return conn, nil
}

func declareQueue(channel *amqp.Channel) (amqp.Queue, error) {
	queue, err := channel.QueueDeclare(
		"integracoes-offline",
		true,
		false,
		false,
		true,
		nil,
	)

	if err != nil {
		return amqp.Queue{}, err
	}

	return queue, nil
}

func SendMessage(conn *amqp.Connection, message string) error {
	channel, err := conn.Channel()
	if err != nil {
		return err
	}

	queue, err := declareQueue(channel)
	if err != nil {
		return err
	}

	err = channel.Publish(
		"",
		queue.Name,
		true,
		false,
		amqp.Publishing {
			ContentType: "text/plain",
			Body: []byte(message),
		},
	)

	defer channel.Close()

	return err
}