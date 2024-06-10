#importar datos
datos <- file.choose()
bookings_df <- read.csv(datos, fileEncoding = "Latin1", check.names = FALSE)
head(bookings_df)
str(bookings_df)
glimpse(bookings_df)
colnames(bookings_df)
skim_without_charts(bookings_df)
#crear un nuevo marco con hotel,is_canceled y lead_time
trimmed_df <- bookings_df %>% select(hotel, is_canceled, lead_time)
#renombrar una columna
rename(trimmed_df, hotel_type = hotel)
#unir datos de columnas
example_df <- bookings_df %>%
  select(arrival_date_year, arrival_date_month) %>%
  unite(arrival_month_year, c("arrival_date_month", "arrival_date_year"), sep = " ")
example_df <- bookings_df %>%
  mutate(guests = adultos + ninos + bebes)
head(example_df)
practica_df <- bookings_df %>%
  summarize(number_canceled = sum(is_canceled),
            average_lead_time = mean(lead_time))
head(practica_df)
            