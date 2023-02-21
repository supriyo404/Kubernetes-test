node{
    def IMAGE_TAG= BUILD_NUMBER
    def DOCKER_REPO= "supriyo404"
    def IMAGE_NAME= "portain-image"
    stage("Checkout Code"){
        
        git url: "https://github.com/supriyo404/Kubernetes-test.git", branch: "main"
    }
    stage('Maven Clean Package'){
        echo 'building JAR file..'
        def mavenHome = tool name:"Maven3", type:'maven'
        sh "${mavenHome}/bin/mvn clean package -DskipTests"
    }
    stage('Build Docker Image'){

        sh "docker build -t ${DOCKER_REPO}/${IMAGE_NAME}:${IMAGE_TAG} ."
    }
    stage('Push to Registry'){
        withCredentials([string(credentialsId: 'dockerSecret', variable: 'dockerSecret')]) {
            sh "docker login -u supriyo404 -p ${dockerSecret}" //add host name with port of repo at the end if not default dockerhub
        }
        
        sh "docker push ${DOCKER_REPO}/${IMAGE_NAME}:${IMAGE_TAG}"
        sh "docker rmi ${DOCKER_REPO}/${IMAGE_NAME}:${IMAGE_TAG}"
        sh "docker rmi ${DOCKER_REPO}/${IMAGE_NAME}:8"
    }
    stage('Deploy to DockerPi'){
        sshagent(['dockerPihost']) {
        sh "ssh -o StrictHostKeyChecking=no pi@192.168.1.15 docker rm -f dockertestcont || true"
        sh "ssh -o StrictHostKeyChecking=no pi@192.168.1.15 docker run -d -p 8091:8080 --name dockertestcont ${DOCKER_REPO}/${IMAGE_NAME}:${IMAGE_TAG}"
        
        }
        
    }
    
}
