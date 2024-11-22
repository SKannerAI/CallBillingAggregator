Docker commands

#docker build -t <image-name>:<tag> .
docker build -t myapp:latest .

#List the docker images for verification
docker images
#Run the image by providing the image & container(your choice) name
docker run --name <container-name> <image-name>

#To see your running containers, use:
docker ps (optional param -a to see all containers including, stopped ones)

#When you’re done, stop the container:
docker stop <container-id>
docker rm <container-id>
