pipeline {
    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '7'))
    }
    tools {
        jdk 'JDK-11'
        maven 'Maven-3.8.6'
    }
    triggers {
        cron('H 9 * * *')  // Fixed cron expression syntax
    }
    environment {
        GITHUB_TOKEN = credentials('github-token-credential-id')
        REPORT_REPO = 'https://github.com/Prathameshgeek/endlink-test-reports.git'
        BUILD_TIMESTAMP = "${BUILD_NUMBER}_${new Date().format('yyyy-MM-dd_HH-mm')}"
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', credentialsId: 'github-repo-access', url: 'https://github.com/Prathameshgeek/Endlink_WebAutomation.git'
            }
        }
        stage('Build and Test') {
            steps {
                sh 'mvn clean install' 
            }
        }
        stage('Publish Report to GitHub Pages') {
            steps {
                withCredentials([string(credentialsId: 'github-token-credential-id', variable: 'GITHUB_TOKEN_VAR')]) {
                    sh '''
                        git config --global user.email "prathamesh@geekyants.com"
                        git config --global user.name "Jenkins"
                        rm -rf ./temp-reports || true
                        mkdir -p ./temp-reports
                        cd ./temp-reports
                        git clone https://${GITHUB_TOKEN_VAR}@github.com/Prathameshgeek/endlink-test-reports.git .
                        
                        # Create both reports and latest directories
                        mkdir -p ./reports/${BUILD_NUMBER}_$(date +"%Y-%m-%d_%H-%M")
                        mkdir -p ./latest
                        
                        # Copy reports to timestamped directory
                        cp -r ../Reports/* ./reports/${BUILD_NUMBER}_$(date +"%Y-%m-%d_%H-%M")/
                        
                        # Create index.html in the timestamped directory
                        cp ./reports/${BUILD_NUMBER}_$(date +"%Y-%m-%d_%H-%M")/ExtentReport.html ./reports/${BUILD_NUMBER}_$(date +"%Y-%m-%d_%H-%M")/index.html
                        
                        # Clear latest directory and copy new files
                        rm -rf ./latest/* || true
                        cp -r ./reports/${BUILD_NUMBER}_$(date +"%Y-%m-%d_%H-%M")/* ./latest/
                        
                        # Commit and push changes
                        git add .
                        git commit -m "Add test report for build ${BUILD_NUMBER}" || echo "No changes to commit"
                        git push origin main
                    '''
                    script {
                        def timestamp = sh(script: 'date +"%Y-%m-%d_%H-%M"', returnStdout: true).trim()
                        env.REPORT_URL = "https://prathameshgeek.github.io/endlink-test-reports/reports/${BUILD_NUMBER}_${timestamp}/"
                        env.LATEST_REPORT_URL = "https://prathameshgeek.github.io/endlink-test-reports/latest/"
                    }
                }
            }
        }
    }
    post {
        always {
            publishHTML([
                allowMissing: false, 
                alwaysLinkToLastBuild: true, 
                keepAll: false, 
                reportDir: 'Reports', 
                reportFiles: 'ExtentReport.html',
                reportName: 'Extent Report'
            ])
            emailext(
                to: 'prathamesh@geekyants.com' , 
                subject: "Jenkins Build Report - Endlink Webapp",
                body: """
                <p>Hi Team,</p>
                <p>This email provides the Endlink web application automation build report. Below is the status of the latest automated testing.</p>
                <h2>Jenkins Build Report</h2>
                <p><b>Job:</b> Endlink Web_Automation Pipeline</p>
                <p><b>Build Status:</b> ${currentBuild.currentResult}</p>
                <p><b>Extent Report:</b> <a href="${env.LATEST_REPORT_URL}">View Latest Report</a></p>
                <br>
                <p>Regards, <br> Geekyants QA Team</p>
                """,
                mimeType: 'text/html',
                attachLog: true
            )
        }
    }
}
