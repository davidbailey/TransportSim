from kafka import KafkaClient , KeyedProducer
kafka = KafkaClient('localhost:9092')

producer = KeyedProducer(kafka)

producer.send_messages(b'people', b'1', b'')
producer.send_messages(b'cars', b'1', b'{ "type": "Feature", "id": "1234", "geometry": {"type": "Point", "coordinates": [-118.385, 34.065] } }')
producer.send_messages(b'bicycles', b'1', b'')

