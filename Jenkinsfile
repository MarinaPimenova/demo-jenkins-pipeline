node {
    stage 'Clone the project'
    git 'https://github.com/MarinaPimenova/demo-jenkins-pipeline.git'

    dir('demo-jenkins-pipeline') {
        stage("Compilation") {
            sh "./mvnw clean install -DskipTests"
        }

        stage("Tests and Deployment") {
            parallel 'Unit tests': {
                stage("Runing unit tests") {
                    try {
                        sh "./mvnw test -Punit"
                    } catch(err) {
                        step([$class: 'JUnitResultArchiver', testResults:
                          '**/target/surefire-reports/TEST-*UnitTest.xml'])
                        throw err
                    }
                   step([$class: 'JUnitResultArchiver', testResults:
                     '**/target/surefire-reports/TEST-*UnitTest.xml'])
                }
            }, 'Integration tests': {
                stage("Runing integration tests") {
                    try {
                        sh "./mvnw test -Pintegration"
                    } catch(err) {
                        step([$class: 'JUnitResultArchiver', testResults:
                          '**/target/surefire-reports/TEST-'
                            + '*IntegrationTest.xml'])
                        throw err
                    }
                    step([$class: 'JUnitResultArchiver', testResults:
                      '**/target/surefire-reports/TEST-'
                        + '*IntegrationTest.xml'])
                }
            }

            stage("Staging") {
                sh "pid=\$(lsof -i:8989 -t); kill -TERM \$pid "
                  + "|| kill -KILL \$pid"
                withEnv(['JENKINS_NODE_COOKIE=dontkill']) {
                    sh 'nohup ./mvnw spring-boot:run -Dserver.port=9094 -DGOOGLE_API_KEYAIzaSyDIsbsXe7G1OkrFcGsgi_cAGGRiQ1gC_lI &'
                }
            }
        }
    }
}