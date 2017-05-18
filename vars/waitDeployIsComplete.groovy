#!/usr/bin/groovy

def call(project, app){
  loginOpenshift(project){
    timeout(time: 2, unit: 'MINUTES') {
      waitUntil{
        def output = sh script: '''
        export out=1
        for e in $(oc get dc ${app} --template '{{range .status.conditions}}{{.status|println}}{{end}}') ; do
          if [ "${e}" != "False" ] ; then
            out=0
          fi
        done
        return ${out}
        ''', returnOutput: true
      }
    }
  }
}
