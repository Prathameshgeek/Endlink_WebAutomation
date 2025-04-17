pipeline {
    agent any
    tools {
        jdk 'JDK-11'
        maven 'Maven-3.8.6'
    }
    triggers {
        cron('H 9 * * *')
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', credentialsId: 'github-repo-access', url: 'https://github.com/Prathameshgeek/Endlink_WebAutomation.git'
            }
        }
        stage('Build and Test') {
            steps {
                sh 'mvn clean install' // Execute Maven from the workspace root
                sh 'pwd'
                sh 'ls -al target'
            }
        }
    }
    post {
        always {
            publishHTML([
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: false,
                reportDir: 'Endlink_WebAutomation/Reports', // Adjust path if needed
                reportFiles: 'extent-report.html',
                reportName: 'Endlink Webapp Extent Report'
            ])
            emailext(
                to: 'prathamesh@geekyants.com',
                subject: "Jenkins Build Report - Endlink Webapp",
                body: """
                    <h2>Jenkins Build Report</h2>
                    <p><b>Job:</b> Endlink Web_Automation Pipeline</p>
                    <p><b>Build Status:</b> ${currentBuild.currentResult}</p>
                    <p><b>Extent Report:</b> <a href="${BUILD_URL}artifact/Endlink_WebAutomation/Reports/extent-report.html">View Report</a></p>
                    <br>
                    <p>Regards, <br> Jenkins Pipeline</p>
                """,
                mimeType: 'text/html',
                attachLog: true,
                attachmentsPattern: 'Endlink_WebAutomation/Reports/extent-report.html' // Adjust path if needed
            )
        }
    }
}
