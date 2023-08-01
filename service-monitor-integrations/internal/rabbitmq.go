package internal

import (
	"log"
	"github.com/streadway/amqp"
)

func ConnectToRabbitMQ() (*amqp.Connection, error) {
	conn, err := amqp.Dial("amqp://guest:guest@localhost:5672/")
	if err != nil {
		return nil, err
	}

	defer conn.Close()

	channel, err := createChannel(conn)
	if err != nil {
		return nil, err
	}

	queue, err := declareQueue(channel)
	if err != nil {
		return nil, err
	}

	err = sendMessage(channel, queue, "Testando")

	err = consumeMessages(channel, queue)

	return conn, nil
}

func createChannel(conn *amqp.Connection) (*amqp.Channel, error){
	channel, err := conn.Channel()
	if err != nil {
		return nil, err
	}

	return channel, nil
}

func declareQueue(channel *amqp.Channel) (amqp.Queue, error) {
	queue, err := channel.QueueDeclare(
		"teste",
		false,
		false,
		false,
		false,
		nil,
	)

	if err != nil {
		return amqp.Queue{}, err
	}

	return queue, nil
}

func sendMessage(channel *amqp.Channel, queue amqp.Queue, message string) error {
	err := channel.Publish(
		"",
		queue.Name,
		false,
		false,
		amqp.Publishing {
			ContentType: "text/plain",
			Body: []byte(message),
		},
	)

	return err
}

func consumeMessages(channel *amqp.Channel, queue amqp.Queue) error {
	messages, err := channel.Consume(
		queue.Name,
		"",
		true,
		false,
		false,
		false,
		nil,
	)

	if err != nil {
		return err
	}

	go func() {
		for message := range messages {
			log.Printf("Mensagem recebida: %s", message.Body)
		}
	} ()

	return nil
}