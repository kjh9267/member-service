node {
    stage('clone') {
        checkout scm
    }
    stage('build') {
        image = docker.build("kjh9267/member-service")
    }
    stage('push') {
        docker.withRegistry('https://ghcr.io', 'github_credential') {
            image.push("$BUILD_ID")
        }
    }
}