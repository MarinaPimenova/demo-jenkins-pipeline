pipeline {
    agent any
    environment {
        GIT_REPO_URL        = 'https://github.com/MarinaPimenova/demo-jenkins-pipeline.git'
        GITHUB_CREDS_ID     = 'jenkins-webhook'
        GOOGLE_API_KEY     = 'AIzaSyDIsbsXe7G1OkrFcGsgi_cAGGRiQ1gC_lI' //credentials('google-api-key')
    }

    parameters {
        choice(name: 'Build tool: ',
        choices:['maven', 'gradle'],
        description: 'Only maven is available. Gradle is under construction...')
        gitParameter(
            branchFilter:   'origin/(.*)',
            defaultValue:   'master',
            name:           'BRANCH',
            type:           'PT_BRANCH')
    }

    stages {
        stage('Checkout') {
            steps {
                echo "Running ${env.BUILD_ID} on ${env.JENKINS_URL}"
                echo "Job name: ${env.JOB_NAME}"
                echo "Node name: ${env.NODE_NAME}"
                sh 'printenv'
                echo 'Checkout..'
                git branch: params.BRANCH, env.GIT_REPO_URL, credentialsId: env.GITHUB_CREDS_ID
            }
        }
        stage('Build') {
            steps {
                echo 'Building..'
                sh "./mvnw clean install -DskipTests"
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
                sh "./mvnw test"
            }
        }
        stage('Deploy') {
            when {
              expression {
                currentBuild.result == null || currentBuild.result == 'SUCCESS'
              }
            }
            environment {
                ENKINS_NODE_COOKIE=dontkill
            }
            steps {
                echo 'Deploying....'
                //sh 'nohup ./mvnw spring-boot:run -Dserver.port=8989 -DGOOGLE_API_KEY=$GOOGLE_API_KEY &'
                //nohup java -DYOUR_ACCESS_KEY= -DYOUR_SECRET_KEY= -jar /home/ubuntu/mp-order-report.jar &
            }
        }
    }
}