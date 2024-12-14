#!/bin/bash

# Exit on any error
set -e

# Variables
AWS_REGION="us-east-1"
ACCOUNT_ID="717279688908"
REPO_NAME="repositorio"
IMAGE_TAG="v8"  # Defina a tag da imagem aqui

# Diretório de contexto é o diretório pai onde o código fonte está localizado
CONTEXT_DIR=".."  # Um nível acima, onde está o código fonte e outros arquivos

# Certifique-se de que o Dockerfile existe
if [ ! -f "Dockerfile" ]; then
    echo "Dockerfile not found. Please check the path."
    exit 1
fi

# Build Docker image
docker build -t $REPO_NAME:$IMAGE_TAG -f Dockerfile $CONTEXT_DIR || { echo "Docker build failed"; exit 1; }

# Tag Docker image
docker tag $REPO_NAME:$IMAGE_TAG $ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/$REPO_NAME:$IMAGE_TAG || { echo "Docker tag failed"; exit 1; }

# Login to ECR
AWS_LOGIN_PASSWORD=$(aws ecr get-login-password --region $AWS_REGION) || { echo "AWS ECR login password retrieval failed"; exit 1; }
echo $AWS_LOGIN_PASSWORD | docker login --username AWS --password-stdin $ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com || { echo "Docker login failed"; exit 1; }

# Push Docker image to ECR
docker push $ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/$REPO_NAME:$IMAGE_TAG || { echo "Docker push failed"; exit 1; }

echo "Docker image successfully pushed to ECR."


