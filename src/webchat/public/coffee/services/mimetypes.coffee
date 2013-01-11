angular.module('WebChat').factory '_MimeTypes', ->

    class MimeTypes

        constructor: (@path="/assets/images/mimetypes/") ->
            

        getIconPath: (key) ->
            key = key.replace('/', '-')
            fullPath = @path + key + '.png'
            return fullPath


    return MimeTypes
