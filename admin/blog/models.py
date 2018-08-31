from django.db import models
from django.utils import timezone


class Parkinformation(models.Model):
    author = models.ForeignKey('auth.User', on_delete=models.CASCADE)
    park_name = models.CharField(max_length=200)
    X_coordinate = models.CharField(max_length=7, default=0)
    Y_coordinate = models.CharField(max_length=7, default=0)
    Description = models.TextField()
    created_date = models.DateTimeField(
            default=timezone.now)
    published_date = models.DateTimeField(
            blank=True, null=True)

    def publish(self):
        self.published_date = timezone.now()
        self.save()

    def __str__(self):
        return self.park_name

class Pathinformation(models.Model):
    author = models.ForeignKey('auth.User', on_delete=models.CASCADE)
    path_name = models.CharField(max_length=200)
    Description = models.TextField()
    created_date = models.DateTimeField(
            default=timezone.now)
    published_date = models.DateTimeField(
            blank=True, null=True)

    def publish(self):
        self.published_date = timezone.now()
        self.save()

    def __str__(self):
        return self.path_name
