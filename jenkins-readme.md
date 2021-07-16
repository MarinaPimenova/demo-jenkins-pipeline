## Module CI/CD + Jenkins
### Task CI/CD using Jenkins

2.1 Setup Jenkins locally ->
sudo su -
docker run --name jenkins --rm -u root -d -p 8080:8080 -p 50000:50000 -v /var/run/docker.sock:/var/run/docker.sock -v /home/jenkins:/var/jenkins_home jenkins/jenkins:lts

2.2 For CI/CD you should use project from build tools home work -> https://github.com/MarinaPimenova/demo-jenkins-pipeline.git

2.3 Use names for job described in brackets (name: $nameOfJob)  -> ???


#### 2.4 Create build job (name: release) -

2.4.1 Should build when SCM has changes - https://github.com/tinexw/cdc-with-pact/issues/6

2.4.2 Job should be parameterized with maven or grade (user should be able to choose it in job parameter)

2.4.3 Use promotion plugin. Should be available two promo stars

2.4.3.1 First stars should be applied automatically to successful build (QA)
2.4.3.2 Second stars should be applied manually (Prod)

#### 2.5 Create continuous Job (name: continuous)

2.5.1 Should build every night at 2:00 AM

#### 2.6 Create Deploy Job (name: deploy)

2.6.1 Should be parameterized with artifacts prompted with QA star

2.6.2 Use Container Deploy plugin

#### 2.7 Setup machine on cloud.epam.com -> ??? aws.cloud EC2 -ok?

2.7.1 Setup additional user for Jenkins

2.7.2.    Setup tomcat

2.7.3.    Setup cloud machine as slave on Jenkins

#### 2.8. Create deploy job (name: deploy_cloud)

2.8.1.    Should deploy on cloud machine

2.8.2.    Should be parameterized with artifacts promoted with Prod star

2.8.3.    Use Container Deploy plugin

#### 2.9. Create PipeLine (delivery_pipeline)

2.9.1.    Job should be parameterized with maven or gradle (user should be able to choose it in job parameter)

2.9.2.    Step Build according to parameter (release)

2.9.3.    Step Deploy on test (deploy job)

2.9.4.    Check using curl that deployment is successful (https://curl.haxx.se/download.html)
??? if deployment is successful deploy on cloud (deploy_cloud) 

