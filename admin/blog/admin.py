from django.contrib import admin
from .models import Park
from .models import Route
from .models import Contact
from .models import Image
from .models import Walks

admin.site.register(Park)

admin.site.register(Route)

admin.site.register(Contact)

admin.site.register(Image)

admin.site.register(Walks)
