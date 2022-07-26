pipeline{
    agent any
    parameters{
        string(name: 'BRANCH', defaultValue: '', description: 'give branch name')
        string(name: 'SERVER_IP', defaultValue: '', description: 'give Ip address')
        string(name: 'BUID_NUMBER', defaultValue: '', description: "give build num")
    }
    stages{
        stage('downloading artifacts'){
        steps{
            println "downloading artifacts from s3"
            sh """
            aws s3 ls s3://alankruthiart/application
            aws s3 cp s3://alankruthiart/application/${BRANCH}/hello-${BULD_NUM}.war .
            """
        }
    }
    stage('copying artifact'){
        steps{
            println "copying artifacts"
            sh "scp -o StrictHostKeyChecking=no -i/tmp/alankruthi21.pem.pem hello-${BUILD_NUMBER}.war ec2-user@${SERVER_IP}:/tmp/"
            sh "ssh -i /tmp/alankruthi21.pem.pem ec2-user@${SERVER_IP}.war \"sudo cp /tmp/hello-${BUILD_NUMBER}.war /var/lib/tomcat/webapps\""
        }
    }
        }
    }
