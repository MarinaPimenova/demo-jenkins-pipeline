pipeline {

agent any
/*     agent {
       docker {
         image 'bibinwilson/jenkins-slave:latest'
       }
    } */
    //'AIzaSyDIsbsXe7G1OkrFcGsgi_cAGGRiQ1gC_lI'
    environment {
        GIT_REPO_URL        = 'https://github.com/MarinaPimenova/demo-jenkins-pipeline.git'
        GITHUB_CREDS_ID     = 'jenkins-webhook'
        GOOGLE_API_KEY     = credentials('google-api-key')
        EC2_PEM             = credentials('ec2-pem')
    }

    parameters {
        choice(name: 'Build tool: ',
        choices:['maven', 'gradle'],
        description: 'Only maven is available. Gradle is under construction...')
    }

    stages {
        stage('Info') {
            steps {
                echo "Running ${env.BUILD_ID} on ${env.JENKINS_URL}"
                echo "Job name: ${env.JOB_NAME}"
                echo "Node name: ${env.NODE_NAME}"
                sh 'printenv'

                //git branch: params.BRANCH, url: env.GIT_REPO_URL, credentialsId: env.GITHUB_CREDS_ID
                //git branch: 'master', url: 'https://github.com/MarinaPimenova/demo-jenkins-pipeline.git', credentialsId: 'jenkins-webhook'
            }
        }
        stage('Build') {
            steps {
                echo 'Building..'
                sh "pwd"
                sh "ls -la"
                sh "chmod +x mvnw"
                sh "ls -la"

                sh "./mvnw clean install -DskipTests"
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
                sh "./mvnw test -Punit"
            }
        }
        stage('Deploy') {
            when {
              expression {
                currentBuild.result == null || currentBuild.result == 'SUCCESS'
              }
            }
            environment {
                JENKINS_NODE_COOKIE='dontkill'
            }
            steps {
                echo 'Deploying....' //ssh -i "ec2-mp-ubuntu.pem" ubuntu@ec2-44-193-25-229.compute-1.amazonaws.com
                sh 'echo $EC2_PEM >> ec2-mp-ubuntu.pem' //http:/ec2-44-193-25-229.compute-1.amazonaws.com:8080/
                sh 'scp -P 22 -i ./ec2-mp-ubuntu.pem **/target/*.war ubuntu@ec2-44-193-25-229.compute-1.amazonaws.com:/opt/tomcat/webapps/demo.war'
                //sh 'nohup ./mvnw spring-boot:run -Dserver.port=8989 -DGOOGLE_API_KEY=$GOOGLE_API_KEY &'
                //nohup java -DYOUR_ACCESS_KEY= -DYOUR_SECRET_KEY= -jar /home/ubuntu/mp-order-report.jar &
            }
        }
    }
}