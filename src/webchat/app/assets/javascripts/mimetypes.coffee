###
Maps mimetypes to mimetype icons
###

class MimeTypes

    constructor: () ->
        @path = "/assets/images/mimetypes/"
        @mimetypes = 
            "application/pdf": "application-pdf.png"
            "text/xml": "text-xml.png"
        
    get_mimetype_icon_path: (key) ->
        full_path = @path + @mimetypes[key]
        console.log full_path
        return full_path
        
