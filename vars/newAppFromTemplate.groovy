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
    def exist = sh(script:'''
      oc get dc "${config.name} -n ${config.project}"
    ''', returnStatus: true)
    if (!exist){
      sh "oc process openshift//${config.template} ${params} | oc replace ${config.name} -n ${config.project} -f -"
    }else{
      sh "oc new-app --name=${config.name} --template=${config.template} ${params} -n ${config.project}"
    }
  }
}
