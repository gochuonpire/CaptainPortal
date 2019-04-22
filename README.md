# CaptainPortal
CaptainPortal 1.1

Requirements: CaptainLock, CaptainWarp

Configuration: /plugins/CaptainPortal/config.yml
    - total_portals_allowed : set the total numbers of portals allowed to be created
    - create_read_me : if set to true, will place readme.txt in /plugins/CaptainPortal/ If you delete readme.txt it will be replaced unless you set this to false.

Features: A condensed rundown of things available
    - Portals
        - Create a zone which when entered will teleport a player to a specified warp exit point (if they have permission to access it)

Commands:
    /portal: Commands with {} denote optional arguments, <> denote necessary parameters

        *blank*: 
            - Shows help menu. Shows commands available to the player based on their permissions

        list:
            - Shows portals to player that are available to them based on their permissions

        pause {portal}:
            - If a portal is specified, pause the portal
            - If none is specified, pause all portals

        resume {portal}:
            - If a portal is specified, resume the portal
            - If none is specified, resume all portals

        info <portal>:
            - Shows basic info of <portal>
            - If player has captainportal.*, captainportal.info they can see all info and hidden warps
            - Other players cannot see portals they do not have access to
        
        remove <portal>:
            - Remove the specified portal

        air <portal> <warp>:
            - Add the portal with the specified name. Will tp to the designated warp exit.
            - Designed to create portals in midair (vertical/horizontal)
            - Fills in with cobwebs (for now
            - To set zone, just hit air with diamond as directed

        add <portal> <warp> <offset_y>:
            - Add the portal with the specified name. Will tp to the designated warp exit. 
            - To set zone, hit block with diamond as directed
            - Offset Y denotes the extra Y block "cushion" that is afforded to the zone. If 0, only the specified zone will be teleported.
                If 1 or more, will add a cushion on both the top and bottom of the zone which will also teleport the player
