pipeline{
    agent any
    parameters{
        string(name: 'BRANCH', defaultValue: 'master', description: 'give branch name')
        string(name: 'SERVER_IP', defaultValue: '', description: 'give IP address')
        string(name: 'BUILD_NUMBER', defaultValue: '', description: 'give build num')
    }
    stages{
        stage("download artifacts"){
            steps{
                println "downloading artifacts from s3"
                sh """
                aws s3 ls s3://alankruthiarts/application
                aws s3 cp s3://alankruthiarts/application/${BRANCH}/hello-${BUILD_NUMBER}.war .
                """
            }
        }
        stage("copy artifact"){
            steps{
                println "copying artifacts "
               // sh "ssh -i /tmp/nvirginia.pem ec2-user@${SERVER_IP} "\systemctl status tomcat\""
                sh "scp -o StrictHostKeyChecking=no -i /tmp/alankruthi21.pem.pem hello-${BUILD_NUMBER}.war ec2-user@${SERVER_IP}:/tmp/"
                sh "ssh -i /tmp/alankruthi21.pem.pem ec2-user@${SERVER_IP} \"sudo cp /tmp/hello-${BUILD_NUMBER}.war /var/lib/tomcat/webapps\""

            }
        }
    }
}