import requests
import time
import random

instruments = ["ABC", "DEF", "GHI", "JKL", "MNO", "PQR", "STU", "VWX", "YZ"]

def MillisecondTime():
    return int(time.time()*1000.0)

for item in range(1000):
    payload = {"instrument": instruments[random.randint(0, len(instruments)-1)], "price": random.uniform(1, 100), "timestamp": MillisecondTime()}
    response = requests.post('http://localhost:8080/ticks', json=payload)

    if response.status_code == 201:
        print(f"Tick {item} created {response}")
    elif response.status_code == 204:
        print(f"Tick {item} not created {response}")
    else:
        print(f"Another problem {response}")

for instrument in instruments:
    response = requests.get('http://localhost:8080/statistics/'+instrument)
    if response.status_code != 200:
        print(f"Response failed {response}")

    print(response.json())

response = requests.get('http://localhost:8080/statistics/')
if response.status_code != 200:
    print(f"Response failed {response}")

print(response.json())
