export const Services = () : Array<Service> => { return window.settings().services }

export const getService = (path: string | undefined): Service | undefined => {
  if(path === undefined){
    return undefined;
  }
  return window.settings().services.find(s => s.path === path)
}

export const hasOpenApi = (service: Service | undefined) : boolean => {
  return service != undefined && service.openapi != undefined && service.openapi.url != undefined;
}

export const hasAsyncApi = (service: Service | undefined) : boolean => {
  return service != undefined && service.asyncapi != undefined && service.asyncapi.url != undefined;
}


