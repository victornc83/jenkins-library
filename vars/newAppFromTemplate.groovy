#!/usr/bin/groovy

def call(body){

  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  def params = ''
  loginOpenshift(config.project){
    for(int i = 0; i<config.parameters.size();i++){
      params = params + '-p ' + config.parameters[i] + '=' + config.values[i] + ' '
    }
    def noexist = sh(script:"oc get dc ${config.name} -n ${config.project}",returnStatus: true)
    if (!noexist){
      sh "oc export secret/${config.name} -n ${config.project} -o yaml > secrets.yml"
      sh "oc process openshift//${config.template} ${params} | oc replace -n ${config.project} -f - 2>&1 | grep -v Service"
      sh "oc replace -f secrets.yml -n ${config.project}"
    }else{
      sh "oc new-app --name=${config.name} --template=${config.template} ${params} -n ${config.project}"
    }
  }
}
