###
Maps keycodes to smileys
###

class Smileys

    constructor: () ->
        @path = "/assets/images/smileys/"
        @smileys = 
            ":)": "080.gif"
            "-_-": "107.gif"
            ":/": "003.gif"
            ":(": "030.gif"
            ":D": "074.gif"
            "xD": "049.gif"
            ";D": "073.gif"
            "&gt;.&lt;": "009.gif"
            "-.-": "064.gif"
            "-.-*": "064.gif"
            ";)": "083.gif"
            ":P": "048.gif"
            "^^": "055.gif"
            "x(": "010.gif"
            "&lt;3": "112.gif"
            ":blush:": "029.gif"
            ":evil:": "002.gif"
            "lol": "015.gif"
            ":'('": "004.gif"
            ":&gt;" : "054.gif"
            "8)" : "053.gif"
            "Oo": "031.gif"
            "oO": "031.gif"
        
    get_smiley: (key) ->
        return @path + @smileys[key]
        
    get_smileys: () ->
        return @smileys

    
