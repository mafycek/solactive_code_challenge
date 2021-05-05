import requests
import time
import random
import threading

instruments = ["ABC", "DEF", "GHI", "JKL", "MNO", "PQR", "STU", "VWX", "YZ"]
number_of_requests = 100

def MillisecondTime():
    return int(time.time()*1000.0)

def parallel_request(payload):
    response = requests.post('http://localhost:8080/ticks', json=payload)

    if response.status_code == 201:
        print(f"Tick {item} created {response}")
    elif response.status_code == 204:
        print(f"Tick {item} not created {response}")
    else:
        print(f"Another problem {response}")

threads = []
for item in range(number_of_requests):
    payload = {"instrument": instruments[random.randint(0, len(instruments)-1)], "price": random.uniform(1, 100), "timestamp": MillisecondTime()}
    thread = threading.Thread(target=parallel_request, args=(payload,))
    threads.append(thread)
    thread.start()

for item in threads:
    item.join()

for instrument in instruments:
    response = requests.get('http://localhost:8080/statistics/'+instrument)
    if response.status_code != 200:
        print(f"Response failed {response}")

    print(response.json())

response = requests.get('http://localhost:8080/statistics/')
if response.status_code != 200:
    print(f"Response failed {response}")

print(response.json())
