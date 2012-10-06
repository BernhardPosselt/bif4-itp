angular.module('WebChat').factory '_MimeTypes', ->

    class MimeTypes

        constructor: () ->
            @path = "/assets/images/mimetypes/"
            @mimetypes =
                "application/pdf": "application-pdf.png"
                "text/xml": "text-xml.png"
                "text/x-python": "text-x-python.png"
            
        getIconPath: (key) ->
            full_path = @path + @mimetypes[key]
            return full_path


    return MimeTypes
