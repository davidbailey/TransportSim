from kafka import KafkaClient , KeyedProducer
kafka = KafkaClient('localhost:9092')
import json

producer = KeyedProducer(kafka)

cars = {"cars": [{"type": "Feature", "id": 807011662, "geometry": {"type": "Point", "coordinates": [-118.287668,34.264679] }},{"type": "Feature", "id": 1115494253, "geometry": {"type": "Point", "coordinates": [-118.284910,34.262454] }},{"type": "Feature", "id": 1656112982, "geometry": {"type": "Point", "coordinates": [-118.284912,34.262095] }},{"type": "Feature", "id": 103209461, "geometry": {"type": "Point", "coordinates": [-118.294320,34.263207] }},{"type": "Feature", "id": 136105512, "geometry": {"type": "Point", "coordinates": [-118.29356,34.267076] }},{"type": "Feature", "id": 1723962104, "geometry": {"type": "Point", "coordinates": [-118.29080,34.26690] }},{"type": "Feature", "id": 1707068090, "geometry": {"type": "Point", "coordinates": [-118.296152,34.267168] }},{"type": "Feature", "id": 601751984, "geometry": {"type": "Point", "coordinates": [-118.296011,34.266832] }},{"type": "Feature", "id": 822507730, "geometry": {"type": "Point", "coordinates": [-118.296011,34.266832] }},{"type": "Feature", "id": 918577934, "geometry": {"type": "Point", "coordinates": [-118.297265,34.269539] }},{"type": "Feature", "id": 1979841256, "geometry": {"type": "Point", "coordinates": [-118.297931,34.266868] }},{"type": "Feature", "id": 1604018127, "geometry": {"type": "Point", "coordinates": [-118.29356,34.267076] }},{"type": "Feature", "id": 2127593395, "geometry": {"type": "Point", "coordinates": [-118.294693,34.267501] }},{"type": "Feature", "id": 985543876, "geometry": {"type": "Point", "coordinates": [-118.290892,34.266891] }},{"type": "Feature", "id": 961446961, "geometry": {"type": "Point", "coordinates": [-118.293907,34.265554] }},{"type": "Feature", "id": 1254271218, "geometry": {"type": "Point", "coordinates": [-118.288029,34.264371] }},{"type": "Feature", "id": 1437596832, "geometry": {"type": "Point", "coordinates": [-118.294097,34.262547] }}]}

#for car in cars['cars']:
#  car['geometry']['coordinates'] = [car['geometry']['coordinates'][0] + .1,car['geometry']['coordinates'][1] + .1]


producer.send_messages(b'cars', b'1', bytes(json.dumps(cars)))

#producer.send_messages(b'people', b'1', b'')
#producer.send_messages(b'cars', b'1', b'{ "type": "Feature", "id": "1234", "geometry": {"type": "Point", "coordinates": [-118.385, 34.065] } }')
#producer.send_messages(b'bicycles', b'1', b'')

