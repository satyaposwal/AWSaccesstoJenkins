pipeline {
    agent any
    stages {
        stage('Pre-Build') {
            steps {
                echo 'Any Pre-requisite steps before the CodeBuild'
            }
        }
        stage('Build on AWS CodeBuild') {
            steps {
                withAWS(region:'us-east-1',role:'tcp-devops-role',roleAccount:'570730370608') {
                awsCodeBuild credentialsId: 'aws-codeploy-to-dmitesting', 
                credentialsType: 'keys', 
                downloadArtifacts: 'false', 
                environmentTypeOverride: 'LINUX_CONTAINER',                 
                projectName: 'OpenApi-LoadTesting', 
                region: 'us-east-1', 
                reportBuildStatusOverride: 'True', 
                sourceControlType: 'jenkins', 
                sourceLocationOverride: 'tcpopenapi-loadtesting/openapijmeter.zip', 
                sourceTypeOverride: 'S3'
                }
            }    
        }
        stage('Workspace Cleanup') {
            steps{
                cleanWs()
            }
        }
        
       
       
        
    }    
}
