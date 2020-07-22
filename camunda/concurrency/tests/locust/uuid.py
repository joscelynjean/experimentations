import uuid
from locust import HttpUser, between, task

class CamundaUser(HttpUser):
    wait_time = between(1, 2)

    @task
    def push_uuid(self):
        self.client.post("/engine-rest/process-definition/key/uuid-registering/start", json={
	        "variables": {
		        "uuid": {
			        "value": str(uuid.uuid4()),
			        "type":"string"
		        }
	        }
        },
        headers={"authorization": "Basic ZGVtbzpkZW1v"},
        verify=False)
