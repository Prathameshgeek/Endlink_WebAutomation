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
                
                # Create timestamp directory
                TIMESTAMP=$(date +"%Y-%m-%d_%H-%M")
                BUILD_DIR="./reports/${BUILD_NUMBER}_${TIMESTAMP}"
                mkdir -p ${BUILD_DIR}
                mkdir -p ./latest
                
                # Copy all report files to build directory
                cp -r ../Reports/* ${BUILD_DIR}/
                
                # Fix screenshot references in the HTML report
                if [ -f ${BUILD_DIR}/ExtentReport.html ]; then
                    # Make a copy of the original file
                    cp ${BUILD_DIR}/ExtentReport.html ${BUILD_DIR}/index.html
                    
                    # Fix screenshot paths in index.html if screenshots exist
                    if [ -d ${BUILD_DIR}/screenshots ]; then
                        # Replace relative paths in the HTML file
                        sed -i 's|="../screenshots/|="./screenshots/|g' ${BUILD_DIR}/index.html
                        sed -i 's|="../../screenshots/|="./screenshots/|g' ${BUILD_DIR}/index.html
                        sed -i 's|="screenshots/|="./screenshots/|g' ${BUILD_DIR}/index.html
                    fi
                fi
                
                # Clear latest directory and copy new files
                rm -rf ./latest/* || true
                cp -r ${BUILD_DIR}/* ./latest/
                
                # Fix screenshot paths in latest/index.html
                if [ -f ./latest/index.html ] && [ -d ./latest/screenshots ]; then
                    sed -i 's|="../screenshots/|="./screenshots/|g' ./latest/index.html
                    sed -i 's|="../../screenshots/|="./screenshots/|g' ./latest/index.html
                    sed -i 's|="screenshots/|="./screenshots/|g' ./latest/index.html
                fi
                
                # Keep only the latest 5 reports
                cd ./reports
                ls -t | tail -n +6 | xargs rm -rf || true
                cd ..
                
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
                to: 'prathamesh@geekyants.com',
                subject: "Jenkins Build Report - Endlink Webapp",
                body: """
                <h2>Jenkins Build Report</h2>
                <p><b>Job:</b> Endlink Web_Automation Pipeline</p>
                <p><b>Build Status:</b> ${currentBuild.currentResult}</p>
                <p><b>Extent Report:</b> <a href="${env.LATEST_REPORT_URL}">View Latest Report</a></p>
                <p><b>This Build's Report:</b> <a href="${env.REPORT_URL}">View This Build's Report</a></p>
                <br>
                <p>Regards, <br> Geekyants QA Team</p>
                """,
                mimeType: 'text/html',
                attachLog: true
            )
        }
    }
}
