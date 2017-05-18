#!/usr/bin/groovy

def call(project, app){
  loginOpenshift(project){
    timeout(time: 2, unit: 'MINUTES') {
      retry(){
        def output = sh '''
        export out=1
        for e in $(oc get dc ${app} --template '{{range .status.conditions}}{{.status|println}}{{end}}') ; do
          if [ "${e}" != "True" ] ; then
            out=0
          fi
        done
        return ${out}
        ''', returnStatus: true
        return output
      }
    }
  }
}
