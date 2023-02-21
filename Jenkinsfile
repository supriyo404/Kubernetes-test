node{
    def buildNumber= BUILD_NUMBER
    def dockerImage= "supriyo404/portain-image:${buildNumber}"
    stage("Checkout Code"){
        
        git url: "https://github.com/supriyo404/Kubernetes-test.git", branch: "main"
    }
    stage('Maven Clean Package'){
        echo 'building JAR file..'
        def mavenHome = tool name:"Maven3", type:'maven'
        sh "${mavenHome}/bin/mvn clean package -DskipTests"
    }
    stage('Build Docker Image'){

        echo "Building docker Image:${buildNumber}"
        sh "docker build -t ${dockerImage} ."
    }
    stage('Push to Registry'){
        withCredentials([string(credentialsId: 'dockerSecret', variable: 'dockerSecret')]) {
            sh "docker login -u supriyo404 -p ${dockerSecret}" //add host name with port of repo at the end if not default dockerhub
        }
        
        sh "docker push ${dockerImage}"
    }
    
}
