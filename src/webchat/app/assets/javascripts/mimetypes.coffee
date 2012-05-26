###
Maps mimetypes to mimetype icons
###

class MimeTypes

    constructor: () ->
        @path = "/assets/images/mimetypes/"
        @mimetypes = 
            "application/pdf": "application-pdf.png"
        
    get_mimetype_icon_path: (key) ->
        return @path + @mimetypes[key]
        
    
