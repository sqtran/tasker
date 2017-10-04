// Declare microservice name
microservice = 'tasker'
print "microservice: ${microservice}"

// Collect the git info (Since the git plugin has trouble providing the URL and commit hash)
gitURL = "https://tfs.ups.com/tfs/UpsProd/P07AGit_Transportation_Systems/_git/tasker"
gitContextDir = "/"
gitBranch = env.BRANCH_NAME
//TODO get a generic TFS credentials for MOD that does not expire to do git checkout
gitCredentialsId = 'HUBXTFS'
gitCommit = null

// Pull in default Jenkinsfile
jenkinsfileURL = "https://tfs.ups.com/tfs/UpsProd/P07AGit_Transportation_Systems/_git/hubX-infrastructure"
jenkinsfileBranch = "dev"

// Build requests and limits
buildCPURequest = '200m'
buildMemRequest = '1600Mi'
buildCPULimit = '2000m'
buildMemLimit = '1600Mi'
buildWaitTime = '720'

// Deployment requests and limits and base livenessDelay, readinessDelay, and numReplicas
deployCPURequest = '100m'
deployMemRequest = '1500Mi'
deployCPULimit = '1000m'
deployMemLimit = '1500Mi'
numReplicas = 2
livenessDelay = 480
readinessDelay = 20
deploymentWaitTime = numReplicas * livenessDelay * 2000

versionNumber = '1.0'

node("jenkins-agent-base"){

  // Checkout the external Jenkinsfile
  gitCheckout(workspace, jenkinsfileURL, jenkinsfileBranch, 'HEAD', 'hubX-infrastructure', gitCredentialsId)
  print "Checked out infra files"

  gitCheckout(workspace, gitURL, gitBranch, 'HEAD', microservice, gitCredentialsId)
    print "Checked out ${microservice} files"

  //pom = readMavenPom file: 'microservice/pom.xml'
  //versionNumber = pom.version
  //print versionNumber

  pipeline = load 'hubX-infrastructure/master-pipeline/Jenkinsfile'

}

pipeline.main()

/**
 * Clones and checks out the given Git repository branch and commit
 * @param  String workspace     Path of the working directory to use
 * @param  String url           URL of the Git repository
 * @param  String branch        Branch to checkout
 * @param  String commit        Short commit hash to set HEAD to
 * @param  String targetDir     Target directory in the Git repository to clone
 * @param  String credentialsId Id of the Git credentials to use
 *  (From the credentials plugin in Cloudbees)
 */
def gitCheckout(String workspace, String url, String branch, String commit,
                String targetDir, String credentialsId) {
    withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: "${credentialsId}",
                      passwordVariable: 'pass', usernameVariable: 'user']]) {
        // Checkout the code and navigate to the target directory
        int slashIdx = url.indexOf("://")
        String urlWithCreds = url.substring(0, slashIdx + 3) +
                "\"${user}:${pass}\"@" + url.substring(slashIdx + 3);

        sh """
          # Ensure the targetDir is deleted before we clone
          rm -rf ${workspace}/${targetDir}
          git clone -b ${branch} ${urlWithCreds} ${targetDir}
          cd ${workspace}/${targetDir}
          git reset --hard ${commit}
          cd ${workspace}
          echo `pwd && ls -l`
        """
    }
}
