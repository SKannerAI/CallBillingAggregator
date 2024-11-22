***Docker Set Up instructions***

- Build the Docker image:
```bash
Example: docker build -t <your-image-name>:<tag> .
```
```bash
docker build -t my-app:latest .
```
- List the docker images for verification
```bash
docker images
```
- Run the image by providing the image & container(your choice) name
```bash
docker run --name <container-name> <image-name>
```
- To see your running containers, use:
```bash
docker ps (optional param -a to see all containers including, stopped ones)
```
- When you’re done, stop the container:
```bash
docker stop <container-id>
docker rm <container-id>
```

***K8 Set Up instructions***
1. **Build and Push the Docker Image**

- Build the Docker image:
```bash
docker build -t <your-image-name>:<tag> .
```

- Push the image to a container registry (e.g., Docker Hub, AWS ECR, or GCR):
```bash
docker tag <your-image-name>:<tag> <registry>/<your-image-name>:<tag>
docker push <registry>/<your-image-name>:<tag>
```
  Example:
```bash
docker tag my-app:1.0 my-dockerhub-username/my-app:1.0
docker push my-dockerhub-username/my-app:1.0
```

2. **Set Up a Kubernetes Cluster**

You need a Kubernetes cluster. Here are some common options:
- **Local Development**: Use [Minikube](https://minikube.sigs.k8s.io/docs/) or [Kind](https://kind.sigs.k8s.io/).
- **Cloud Providers**: Use managed Kubernetes services like AKS (Azure), GKE (Google), or EKS (AWS).

Ensure your `kubectl` is configured to connect to your cluster:
```bash
kubectl config view
kubectl get nodes
```

3. **Create Kubernetes Manifests**

You’ll need YAML files to define how Kubernetes will deploy and manage your application. The key resources are:

**a. Deployment**

This defines the pods and their replicas.
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: my-app
spec:
  replicas: 3
  selector:
    matchLabels:
      app: my-app
  template:
    metadata:
      labels:
        app: my-app
    spec:
      containers:
      - name: my-app
        image: <registry>/<your-image-name>:<tag>
        ports:
        - containerPort: 80
```

**b. Service**

This exposes your application inside or outside the cluster.
```yaml
apiVersion: v1
kind: Service
metadata:
  name: my-app-service
spec:
  selector:
    app: my-app
  ports:
    - protocol: TCP
      port: 80
      targetPort: 80
  type: LoadBalancer # or NodePort for local clusters
```

4. **Apply the Manifests**

- Save your YAML files (e.g., `deployment.yaml` and `service.yaml`).
- Apply them using `kubectl`:
  ```bash
  kubectl apply -f deployment.yaml
  kubectl apply -f service.yaml
  ```

- Verify the deployment:
  ```bash
  kubectl get pods
  kubectl get services
  ```

5. **Access the Application**

- If using **LoadBalancer**, note the external IP:
  ```bash
  kubectl get svc my-app-service
  ```

- For **NodePort**, access the application using the Node IP and exposed port:
  ```bash
  minikube service my-app-service
  ```

6. **Iterate and Scale**

- Update your Docker image as needed and push the new version.
- Update your deployment image:
  ```bash
  kubectl set image deployment/my-app my-app=<registry>/<your-image-name>:<new-tag>
  ```

- Scale the deployment:
  ```bash
  kubectl scale deployment my-app --replicas=5
  ```
